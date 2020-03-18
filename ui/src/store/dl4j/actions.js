import axios from 'axios'

export function loadHelperData ({Vue, commit}) {
  return axios.get(jsRoutes.controllers.NNModelController.getNNModelHelper().url)
    .then((response) => {
      commit('setHelperData', response.data);
      return response;
    }).catch((error) => {
        console.log('Error detected ? ' + error);
      throw error
    })
}

export function loadModelToCurrentModel ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getModelData(id).url)
    .then((response) => {
      commit('setCurrentModel', response.data);
      return response;
    }).catch((error) => {
        console.log('Error detected ? ' + error);
      throw error
    })
}

export function persistCurrentModel ({Vue, commit, state}, id) {
  return axios.post(jsRoutes.controllers.NNModelController.persistCurrewntModelData(id).url,
          state.currentModel.rawModel
    )
    .then((response) => {
      commit('setCurrentModel', response.data);
      return response;
    }).catch((error) => {
        console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanLayer ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanLayer(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateDL4JConfigForModel ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getDL4JConfigForModel(id).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateDL4JCodeForModel ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getDL4JCodeForModel(id).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function getAllTrainingStrategies ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getAllTrainingStrategies(id).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function getTrainingStrategy ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getTrainingStrategy(id).url)
    .then((response) => {
      commit('setCurrentTrainingStrategy', response.data);
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function getTrainingStrategyResultsList ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.getTrainingStrategyResultsList(id).url)
    .then((response) => {
      //commit('setCurrentTrainingStrategy', response.data);
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanRecordReader ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanRecordReader(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanInputSplit ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanInputSplit(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanLocalEpochTermCodition ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanLocalEpochTermCodition(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanLocalIterationTermCodition ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanLocalIterationTermCodition(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanLocalScoreCalculator ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanLocalScoreCalculator(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanLocalDatasetIterator ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanLocalDatasetIterator(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function generateCleanPreprocessorNormalizer ({Vue, commit}, type) {
  return axios.get(jsRoutes.controllers.NNModelController.generateCleanPreprocessorNormalizer(type).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}

export function persistCurrentTrainingStrategy ({Vue, commit, state}, id) {
  return axios.post(jsRoutes.controllers.NNModelController.persistCurrentTrainingStrategy(id).url,
          state.currentTrainingStrategy.rawStrategy
    )
    .then((response) => {
      commit('setCurrentTrainingStrategy', response.data);
      return response;
    }).catch((error) => {
        console.log('Error detected ? ' + error);
      throw error
    })
}

/* TEMPORARY */
executeTrainingStrategy
export function executeTrainingStrategy ({Vue, commit}, id) {
  return axios.get(jsRoutes.controllers.NNModelController.executeTrainingStrategy(id).url)
    .then((response) => {
      return response.data;
    }).catch((error) => {
      console.log('Error detected ? ' + error);
      throw error
    })
}
