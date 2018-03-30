package com.anecon.taf.client.data.read;

import java.io.IOException;

public interface TableParser {
    Table readFile() throws IOException;
}
