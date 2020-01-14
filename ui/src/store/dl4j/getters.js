
export function getHelperData (state) {
  return state.helperData;
}

export function getCurrentModel (state) {
  return state.currentModel;
}

export function getCurrentTrainingStrategy (state) {
  return state.currentTrainingStrategy;
}

export function getCurrentModelRaw (state) {
  if (!state.currentModel || !state.currentModel.rawModel)
    return {};
  return state.currentModel.rawModel;
}
