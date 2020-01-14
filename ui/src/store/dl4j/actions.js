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
