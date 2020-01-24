
export function generateTrainingRequest ( trainingStrategyid , requestUUID) {
  return {
    type: 'START_TRAINING',
    strategyId: trainingStrategyid,
    uuid: requestUUID
  }
}


export function generateID() {
  return (Math.random()+"").replace("0.","AAA");
}
