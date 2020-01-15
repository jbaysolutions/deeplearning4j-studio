package com.jbaysolutions.ailabs.builder.testing.local.split;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 15-01-2020
 * Time: 14:34
 */
public class FileSplitWrapper extends InputSplitWrapper{

    public FileSplitWrapper() {
        this.type = InputSplitType.FILE_SPLIT;
    }

    public String rootDir = "";
    public List<String> allowFormat = new ArrayList<>();
    public boolean recursive = true;

}
