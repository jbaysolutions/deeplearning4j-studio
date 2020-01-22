package com.jbaysolutions.ailabs;

import com.jbaysolutions.ailabs.builder.nnwrapper.LayerWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.EpochTerminationConditionWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.IterationTerminationConditionWrapper;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 19-12-2019
 * Time: 19:15
 */
public class ModelHelperData {

    public List<GeneralOption> optimizationAlgorithmList = new ArrayList<GeneralOption>();
    public List<GeneralOption> updaterList = new ArrayList<GeneralOption>();
    public List<GeneralOption> nnLayerTypeList = new ArrayList<GeneralOption>();
    public List<GeneralOption> denseActivationFunctionsList = new ArrayList<GeneralOption>();
    public List<GeneralOption> initWeightFunctionsList = new ArrayList<GeneralOption>();
    public List<GeneralOption> lossFunctionsList = new ArrayList<GeneralOption>();


    public List<GeneralOption> localEpochTerminationCondition = new ArrayList<GeneralOption>();
    public List<GeneralOption> localIterationTerminationCondition = new ArrayList<GeneralOption>();

    public ModelHelperData() {
        optimizationAlgorithmList.add(new GeneralOption(OptimizationAlgorithm.CONJUGATE_GRADIENT.name(), "Conjugate Gradient"));
        optimizationAlgorithmList.add(new GeneralOption(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT.name(), "Stochastic Gradient Descent"));
        optimizationAlgorithmList.add(new GeneralOption(OptimizationAlgorithm.LINE_GRADIENT_DESCENT.name(), "Line Gradient Descent"));
        optimizationAlgorithmList.add(new GeneralOption(OptimizationAlgorithm.LBFGS.name(), "Limited Memory Broyden–Fletcher–Goldfarb–Shanno"));

        updaterList.add(new GeneralOption(MultiLayerWrapper.WrapperUpdater.ADAM.name(), "Adam updater"));
        updaterList.add(new GeneralOption(MultiLayerWrapper.WrapperUpdater.NESTEROVS.name(), "Nesterov's momentum."));
        updaterList.add(new GeneralOption(MultiLayerWrapper.WrapperUpdater.SGD.name(), "SGD (Applies Learning Rate Only)"));

        nnLayerTypeList.add(new GeneralOption(LayerWrapper.LayerType.DENSE.name(), "Dense Layer"));
        nnLayerTypeList.add(new GeneralOption(LayerWrapper.LayerType.CONVOLUTION.name(), "Convolution Layer"));
        nnLayerTypeList.add(new GeneralOption(LayerWrapper.LayerType.SUBSAMPLING.name(), "Sub Sampling Layer"));

        denseActivationFunctionsList.add(new GeneralOption(Activation.RELU.name(), "ReLU - Rectified Linear Unit","f(x) = max(0, x) "));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SOFTMAX.name(), "SOFTMAX", "f_i(x) = exp(x_i - shift) / sum_j exp(x_j - shift) where shift = max_i(x_i)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SIGMOID.name(), "SIGMOID","f(x) = 1 / (1 + exp(-x))"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.CUBE.name(), "CUBE","f(x) = x^3"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.ELU.name(), "ELU"," f(x) = alpha * (exp(x) - 1.0); x < 0 = x ; x>= 0"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.HARDSIGMOID.name(), "Hard SIGMOID","f(x) = min(1, max(0, 0.2*x + 0.5))"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.HARDTANH.name(), "Hard TANH","1, if x >  1 ; -1, if x < -1 ; x, otherwise"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.IDENTITY.name(), "Identity","f(x) = x"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.LEAKYRELU.name(), "Leaky RELU","f(x) = max(0, x) + alpha * min(0, x)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.RATIONALTANH.name(), "Rational tanh approximation","https://arxiv.org/pdf/1508.01292v3"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.RECTIFIEDTANH.name(), "Rectified tanh","Essentially max(0, tanh(x))"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.RELU6.name(), "ReLU6","f(x) = min(max(input, cutoff), 6)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SELU.name(), "SELU - Scaled Exponential Linear Units","https://arxiv.org/pdf/1706.02515.pdf"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SWISH.name(), "SWISH","f(x) = x * sigmoid(x)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.RRELU.name(), "RReLU - Randomized Leaky ReLU","f(x) = max(0,x) + alpha * min(0, x)   :  http://arxiv.org/abs/1505.00853"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SOFTPLUS.name(), "SOFTPLUS","f(x) = log(1+e^x)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.SOFTSIGN.name(), "SOFTSIGN","f_i(x) = x_i / (1+|x_i|)"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.TANH.name(), "TANH","f(x) = (exp(x) - exp(-x)) / (exp(x) + exp(-x))"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.THRESHOLDEDRELU.name(), "Thresholded RELU","f(x) = x for x > theta, f(x) = 0 otherwise. theta defaults to 1.0"));
        denseActivationFunctionsList.add(new GeneralOption(Activation.GELU.name(), "GELU - Gaussian Error Linear Units",""));

        // TO IMPLEMENMTE THJOS OPNE
        // TODO initWeightFunctionsList.add(new GeneralOption(WeightInit.DISTRIBUTION.name(), "Distribution", "Use a given distribution"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.ZERO.name(), "ZERO", "Initialize to a constant value of 0."));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.ONES.name(), "ONES", "Initialize to a constant value of 1."));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.SIGMOID_UNIFORM.name(), "Sigmoid Uniform", "A version of Xavier Uniform for sigmoid activation functions. U(-r,r) with r=4sqrt(6/(fanIn + fanOut))"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.NORMAL.name(), "Normal/Gaussian distribution", "Mean 0 and standard deviation 1/sqrt(fanIn)."));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.UNIFORM.name(), "Uniform distribution", "Uniform U[-a,a] with a=1/sqrt(fanIn). \"Commonly used heuristic\" as per Glorot and Bengio 2010"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.XAVIER.name(), "XAVIER", "As per Glorot and Bengio 2010: Gaussian distribution with mean 0, variance 2.0/(fanIn + fanOut)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.XAVIER_UNIFORM.name(), "XAVIER Uniform", "As per Glorot and Bengio 2010: Uniform distribution U(-s,s) with s = sqrt(6/(fanIn + fanOut))"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.RELU.name(), "RELU", "He et al. (2015), \"Delving Deep into Rectifiers\". Normal distribution with variance 2.0/nIn"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.RELU_UNIFORM.name(), "RELU Uniform", "He et al. (2015), \"Delving Deep into Rectifiers\". Uniform distribution U(-s,s) with s = sqrt(6/fanIn)"));
        // TODO initWeightFunctionsList.add(new GeneralOption(WeightInit.IDENTITY.name(), "IDENTITY", " Weights are set to an identity matrix. Note: can only be used when nIn==nOut."));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.LECUN_UNIFORM.name(), "LECUN Uniform", "Uniform U[-a,a] with a=3/sqrt(fanIn)."));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_NORMAL_FAN_IN.name(), "VAR SCALING NORMAL FAN IN", "Gaussian distribution with mean 0, variance 1.0/(fanIn)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_NORMAL_FAN_OUT.name(), "VAR SCALING NORMAL FAN OUT", "Gaussian distribution with mean 0, variance 1.0/(fanOut)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_NORMAL_FAN_AVG.name(), "VAR SCALING NORMAL FAN AVG", "Gaussian distribution with mean 0, variance 1.0/((fanIn + fanOut)/2)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_UNIFORM_FAN_IN.name(), "VAR SCALING UNIFORM FAN IN", "Uniform U[-a,a] with a=3.0/(fanIn)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_UNIFORM_FAN_OUT.name(), "VAR SCALING UNIFORM FAN OUT", "Uniform U[-a,a] with a=3.0/(fanOut)"));
        initWeightFunctionsList.add(new GeneralOption(WeightInit.VAR_SCALING_UNIFORM_FAN_AVG.name(), "VAR SCALING UNIFORM FAN AVG", "Uniform U[-a,a] with a=3.0/((fanIn + fanOut)/2)"));

        /* LOSS FUNCIONTS */
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.MSE.name(),"MSE - Suared Loss - Mean Squared Error loss"," L = 1/N sum_i (actual_i - predicted)^2"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.L1.name(),"L1 loss function","um of absolute errors, L = sum_i abs(predicted_i - actual_i)"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.XENT.name(),"XENT - Binary cross entropy loss function","https://en.wikipedia.org/wiki/Cross_entropy#Cross-entropy_error_function_and_logistic_regression   - Labels are assumed to take values 0 or 1"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.MCXENT.name(),"MCXENT - Multi-Class Cross Entropy loss function","L = sum_i actual_i * log( predicted_i )"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.KL_DIVERGENCE.name(),"KLD - Kullback Leibler Divergence loss function",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD.name(),"Negative log likelihood loss function","In practice, this is implemented as an alias for {@link LossMCXENT} due to the mathematical equivalence"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.COSINE_PROXIMITY.name(),"Cosine Proximity",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.HINGE.name(),"HINGE",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.SQUARED_HINGE.name(),"Squared HINGE",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR.name()," Mean Absolute Error loss function","L = 1/N sum_i abs(predicted_i - actual_i)"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.L2.name(),"L2 loss function","he L2 loss function is the square of the L2 norm of the difference between actual and predicted.  i.e., sum of squared errors, L = sum_i (actual_i - predicted)^2 "));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.MEAN_ABSOLUTE_PERCENTAGE_ERROR.name(),"Mean Absolute Percentage Error loss function",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR.name(),"Mean Squared Logarithmic Error loss function:","L = 1/N sum_i (log(1+predicted_i) - log(1+actual_i))^2"));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.POISSON.name(),"POISSON (EXPLL)",""));
        lossFunctionsList.add(new GeneralOption(LossFunctions.LossFunction.WASSERSTEIN.name()," Wasserstein loss function","Calculates the Wasserstein distance, also known as earthmover's distance.  https://papers.nips.cc/paper/5679-learning-with-a-wasserstein-loss.pdf"));


        localEpochTerminationCondition.add(new GeneralOption(EpochTerminationConditionWrapper.EpochTerminationConditionType.MAX_EPOCHS.name(), "Maximum Number of Epochs"));
        localEpochTerminationCondition.add(new GeneralOption(EpochTerminationConditionWrapper.EpochTerminationConditionType.BEST_SCORE.name(), "Best Expected Score Achieved"));
        localEpochTerminationCondition.add(new GeneralOption(EpochTerminationConditionWrapper.EpochTerminationConditionType.SCORE_IMPROVEMENT.name(), "Score Improvements"));
        localIterationTerminationCondition.add(new GeneralOption(IterationTerminationConditionWrapper.IterationTerminationConditionType.MAX_SCORE.name(), "Maximum Score"));
    }

    public class GeneralOption{
        public String key;
        public String description;
        public String complete;

        public GeneralOption(String key, String description) {
            this.key = key;
            this.description = description;
        }

        public GeneralOption(String key, String description, String complete) {
            this.key = key;
            this.description = description;
            this.complete = complete;
        }
    }
}
