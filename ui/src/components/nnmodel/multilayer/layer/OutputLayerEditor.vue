<template>
  <div class="card border-danger mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong>Layer {{index}} - Output Layer:</strong>
      <span class="badge badge-pill badge-dark">Num. Nodes: {{layer.numNodes}}</span>
      <span class="badge badge-pill badge-dark">Activation: {{layer.paramsOutput.activation}}</span>
      <span class="badge badge-pill badge-dark">Weight Init: {{layer.paramsOutput.weightInit}}</span>
      <span class="badge badge-pill badge-dark">Loss: {{layer.paramsOutput.lossFunction}}</span>

      <span class=""></span>
      <button
        type="button"
        class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId">
      <div class="card-body">
        <div class="row">
          <div class="col-6">
            <div class="form-group">
              <fieldset>
                <label class="control-label" for="numNodesField">Number of Nodes</label>
                <input
                  v-model="layer.numNodes"
                  v-on:change="notifyChange()"
                  class="form-control" id="numNodesField" type="number"
                  placeholder="Number of nodes this layer has" >
              </fieldset>
            </div>
          </div>
          <div class="col-6">
            <div class="form-group">
              <label class="control-label" for="layerTypeField">Activation Function</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="layer.paramsOutput.activation"
                v-on:change="notifyChange()"
              >
                <option v-for="item in helper.denseActivationFunctionsList" :value="item.key">{{item.description}}</option>
              </select>
              <small id="layerTypeHelp" class="form-text text-muted">{{activationCompleteDescription}}</small>
            </div>
            <div class="form-group">
              <label class="control-label" for="layerTypeField">Weight Initialization</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="layer.paramsOutput.weightInit"
                v-on:change="notifyChange()"
              >
                <option v-for="item in helper.initWeightFunctionsList" :value="item.key">{{item.description}}
                </option>
              </select>
              <small id="layerTypeHelp" class="form-text text-muted">{{weigthInitCompleteDescription}}</small>
            </div>

            <div class="form-group">
              <label class="control-label" for="layerTypeField">Loss Function</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="layer.paramsOutput.lossFunction"
                v-on:change="notifyChange()"
              >
                <option v-for="item in helper.lossFunctionsList" :value="item.key">{{item.description}}</option>
              </select>
              <small id="layerTypeHelp" class="form-text text-muted">{{activationLossDescription}}</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    components: {},
    props: {
      layer: {
        type: Object,
        required: true,
      },
      index: {
        type: Number,
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: false,
      }
    },
    computed: {
      ...mapGetters('dl4j',
          {
            helper: 'getHelperData',
          },
      ),
      activationCompleteDescription() {
        if (this.layer.paramsOutput.activation && this.helper && this.helper.denseActivationFunctionsList) {
          let i = 0 ;
          for ( i = 0 ; i < this.helper.denseActivationFunctionsList.length; i++) {
            if ( this.helper.denseActivationFunctionsList[i].key===this.layer.paramsOutput.activation ) {
              return this.helper.denseActivationFunctionsList[i].complete;
            }
          }
        } else {
          return '';
        }
      },
      weigthInitCompleteDescription() {
        if (this.layer.paramsOutput.weightInit  && this.helper && this.helper.initWeightFunctionsList) {
          let i = 0 ;
          for ( i = 0 ; i < this.helper.initWeightFunctionsList.length; i++) {
            if ( this.helper.initWeightFunctionsList[i].key===this.layer.paramsOutput.weightInit ) {
              return this.helper.initWeightFunctionsList[i].complete;
            }
          }
        } else {
          return '';
        }
      },
      activationLossDescription() {
        if (this.layer.paramsOutput.lossFunction  && this.helper && this.helper.lossFunctionsList) {
          let i = 0 ;
          for ( i = 0 ; i < this.helper.lossFunctionsList.length; i++) {
            if ( this.helper.lossFunctionsList[i].key===this.layer.paramsOutput.lossFunction ) {
              return this.helper.lossFunctionsList[i].complete;
            }
          }
        } else {
          return '';
        }
      },
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      notifyChange() {
        this.$emit('changed');
      }
    }

  }
</script>
