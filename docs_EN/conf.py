#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# -- General configuration ------------------------------------------------
extensions = ['sphinx.ext.todo']
templates_path = ['_templates']
source_suffix = '.rst'
master_doc = 'index'

# General information about the project.
project = 'A2A.TAF'
copyright = u'2017, ANECON Software Design und Beratung GmbH'
author = u'Markus Möslinger'

version = '0.1.0'
release = '0.1.0'

language = 'de'

exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']
pygments_style = 'sphinx'

keep_warnings = True
todo_include_todos = False

# -- Options for HTML output ----------------------------------------------
import sphinx_rtd_theme
html_theme = "sphinx_rtd_theme"
html_theme_path = [sphinx_rtd_theme.get_html_theme_path()]

#html_static_path = ['_static']