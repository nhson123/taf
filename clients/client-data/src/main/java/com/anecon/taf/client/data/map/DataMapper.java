package com.anecon.taf.client.data.map;

import com.anecon.taf.client.data.read.CsvParser;
import com.anecon.taf.client.data.read.ExcelParser;
import com.anecon.taf.client.data.read.Table;
import com.anecon.taf.client.data.read.TableParser;
import com.anecon.taf.core.AutomationFrameworkException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataMapper<T> {
    private static final Logger log = LoggerFactory.getLogger(DataMapper.class);

    private static final Map<Class<?>, Transformer<?>> transformers = new HashMap<>();
    private final Table table;
    private final Class<T> modelClass;

    static {
        registerTransformer(Integer.TYPE, new IntegerTransformer());
        registerTransformer(Integer.class, new IntegerTransformer());
        registerTransformer(String.class, new StringIdentityTransformer());
        registerTransformer(Boolean.TYPE, new BooleanTransformer());
        registerTransformer(Boolean.class, new BooleanTransformer());
        registerTransformer(LocalDateTime.class, new LocalDateTimeTransformer());
        registerTransformer(Float.class, new FloatTransformer());
        registerTransformer(Float.TYPE, new FloatTransformer());
        registerTransformer(Double.class, new DoubleTransformer());
        registerTransformer(Double.TYPE, new DoubleTransformer());
        registerTransformer(BigDecimal.class, new BigDecimalTransformer());
    }

    DataMapper(Table table, Class<T> modelClass) {
        this.table = table;
        this.modelClass = modelClass;
    }

    /**
     * Returns a list of {@code modelClass} instances, mapped from the result of a given {@link TableParser}.
     * This method should be used for reading CSV or Excel files into custom models.
     * <p>
     * Usage example:<br/>
     * <pre>
     * // read from filesystem/csv
     * List<TargetModel> models = DataMapper.toModelList(new CsvParser(new FileInputStream("/path/to/file.csv")), TargetModel.class);
     * // read from HTTP/xlsx
     * List<TargetModel> models = DataMapper.toModelList(ExcelParser.fromXlsx(new URL("http://path.to/file.xlsx).openStream(), TargetModel.class);
     * </pre>
     *
     * @param tableParser An implementation of {@link TableParser} to use for reading a table structure
     * @param modelClass  The target class the contents read from {@code tableParser} should be mapped to
     * @return a list of instances of {@code modelClass}
     * @see CsvParser
     * @see ExcelParser
     */
    public static <T> List<T> toModelList(TableParser tableParser, Class<T> modelClass) {
        try {
            return new DataMapper<>(tableParser.readFile(), modelClass).map();
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            throw new AutomationFrameworkException("Can't map to model", e);
        }
    }

    /**
     * Register a transformer used to transform read {@link String} values into target types used in POJOs.
     *
     * @param targetClass the class the {@link Transformer} should be used for
     * @param transformer an instance of a {@link Transformer}
     * @see Transformer
     */
    public static <T> void registerTransformer(Class<T> targetClass, Transformer<T> transformer) {
        transformers.put(targetClass, transformer);
    }

    List<T> map() throws IllegalAccessException, InstantiationException {
        final List<T> mappedModels = new ArrayList<>(table.getData().size());
        final List<Field> fieldList = fieldList(table.getHeader(), modelClass);

        for (List<String> row : table.getData()) {
            final T model = modelClass.newInstance();
            for (int i = 0; i < row.size(); i++) {

                final Field field = fieldList.get(i);
                final String value = row.get(i);

                if (field == null) {
                    log.warn("Can't find field to map column {} to", i);
                    continue;
                }

                final Class<?> fieldType = field.getType();
                if (!transformers.containsKey(fieldType)) {
                    throw new AutomationFrameworkException("Found no matching transformer for type " + fieldType
                            + " of field " + field.getName() + " and value " + value);
                }

                final Object transformedValue = transformers.get(fieldType).transform(value);
                if (!fieldType.isPrimitive() || transformedValue != null) {
                    FieldUtils.writeField(field, model, transformedValue, true);
                }
            }

            mappedModels.add(model);
        }

        return mappedModels;
    }

    private static List<Field> fieldList(List<String> headers, Class<?> clazz) {
        final Field[] allFields = FieldUtils.getAllFields(clazz);
        final List<Field> fields = new ArrayList<>(allFields.length);

        for (String header : headers) {
            Field fieldToAdd = null;

            for (Field field : allFields) {
                if (header.equalsIgnoreCase(field.getName())) {
                    fieldToAdd = field;
                    break;
                }
            }

            fields.add(fieldToAdd);
        }

        return fields;
    }
}
