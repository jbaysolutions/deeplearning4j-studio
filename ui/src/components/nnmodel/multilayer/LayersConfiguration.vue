<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <i class="fas fa-layer-group"></i>
      Layers Configuration
      <i v-if="updated"
         class="fas fa-info-circle text-warning"
         data-toggle="tooltip"
         data-placement="top"
         title="Modified and requires saving"
         data-original-title="Modified and requires saving"></i>



      <span class="badge badge-pill badge-dark">{{this.totalLayers + 1}} Layers</span>
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

        <div v-if="model && model.layers && model.layers.length > 0" class="row">
          <div v-if="model.layers[0].type==='DENSE'" class="col-12">
            <DenseInputEditor
              :model="model"
              @changed="someChangeHappened"
            />
          </div>
          <div v-if="model.layers[0].type==='CONVOLTION'" class="col-12">
            CONV
          </div>
        </div>


        <!-- ADD LAYERS HERE !-->
        <div class="row mb-3">
          <div class="col-12 text-center">
          <button type="button" class="btn btn-primary" v-on:click="openNewLayerModal(0)">
            <i class="fas fa-plus"></i>
          </button>
          </div>
        </div>

        <!-- LOOP FOR EXISTING LAYERS -->
        <div v-for="(layer, index) in model.layers" class="row">
          <div class="col-12">
            <DenseLayerEditor
              v-if="layer.type==='DENSE'"
              :layer="layer"
              :index="index"
              @changed="someChangeHappened"
            />
          </div>
        </div>

        <!-- ADD LAYERS HERE ASWELL-->
        <div v-if="totalLayers > 0" class="row mb-3">
          <div class="col-12 text-center">
          <button type="button" class="btn btn-primary" v-on:click="openNewLayerModal( totalLayers )">
            <i class="fas fa-plus"></i>
          </button>
          </div>
        </div>

        <!-- OUTPUT LAYER CONFIGURATION -->
        <div class="row mb-3">
          <div class="col-12">
            <OutputLayerEditor
              v-if="model.outputLayer"
              :layer="model.outputLayer"
              :index="model.layers.length"
              @changed="someChangeHappened"
            />
          </div>
        </div>

      </div>
    </div>

    <CreateNNLayerModal
      ref="createLayerModal"
      @newLayerCreated="generateLayer"
    />
  </div>
</template>

<script>
  import CreateNNLayerModal from '../../modals/CreateNNLayerModal';
  import DenseLayerEditor from './layer/DenseLayerEditor';
  import OutputLayerEditor from './layer/OutputLayerEditor';

  import DenseInputEditor from './inputLayer/DenseInputEditor';
  import ConvolutionInputEditor from './inputLayer/ConvolutionInputEditor';
  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
      CreateNNLayerModal,
      DenseLayerEditor,
      OutputLayerEditor,
      DenseInputEditor,
      ConvolutionInputEditor,
    },
    props: {
      model: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        updated: false,
        opened: false,
      }
    },
    computed: {
      totalLayers() {
        if (this.model.layers)
          return this.model.layers.length;
        return 0;
      },
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanLayer: 'generateCleanLayer',
        },
      ),
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      notifyChange() {
        this.$emit('changed');
      },
      openNewLayerModal(index) {
        this.$refs.createLayerModal.openModal(index);
      },
      generateLayer(item) {

        this.generateCleanLayer(item.type)
          .then((response) => {
            console.log("->>>>>>>>>>>> " + JSON.stringify(response));
            if (item.index === 0) {
              this.model.layers.unshift(response);
            }  else {
              this.model.layers.push(response);
            }
            this.someChangeHappened();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
            throw error
          })
      },
      someChangeHappened() {
        this.$emit("changed");
        this.updated = true;
      },
      setUptoDate() {
        this.updated = false;
      }
    }
  }
</script>
