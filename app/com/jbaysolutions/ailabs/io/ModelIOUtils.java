package com.jbaysolutions.ailabs.io;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;

import java.io.*;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 15:54
 */
public class ModelIOUtils {

    public static void saveModel(MultiLayerNetwork network, String filePath) throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(
                        new File(filePath)
                )
        );
        ModelSerializer.writeModel( network, stream, true );
    }

    public static void saveModel(MultiLayerNetwork network, String filePath, DataNormalization normalizer) throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(
                        new File(filePath)
                )
        );
        ModelSerializer.writeModel( network, stream, true, normalizer );
    }


    public static MultiLayerNetwork readModel(String filePath) throws IOException {
        InputStream stream = new FileInputStream(new File(filePath));
        MultiLayerNetwork network = ModelSerializer.restoreMultiLayerNetwork( stream );
        return network;
    }

}
