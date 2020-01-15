<template>
  <div class="row mt-3">
    <div class="col-12">
      <div class="row">
        <div class="col">
          <h1>Model:<b> {{modeldata.name}}</b></h1>
        </div>
        <div class="col">
          <span v-if="updated" class="badge badge-danger">Model was modified, requires saving</span>
        </div>
        <div class="col-1">
          <div v-if="updated" class="text-right">
            <button class="btn btn-primary" v-on:click="saveModel">
              Save
            </button>
          </div>
        </div>
      </div>

      <ul class="nav nav-tabs mb-3">
        <li class="nav-item">
          <a class="nav-link active" data-toggle="tab" href="#definitions">
            Model Definitions
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#training">Training Area</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#runningConfigs">
            Running Configurations
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#deployment">Deployment</a>
        </li>
      </ul>

      <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade active show" id="definitions">

          <div class="row">
            <div class="col text-right mb-3">
              <button v-if="modeldata.status!=='DRAFT' && !updated"
                      class="btn btn-secondary"
                      v-on:click="generateFL4JConfig"
              >
                <i class="fas fa-stream"></i>
                DL4j Conf
              </button>
              <button v-if="modeldata.status!=='DRAFT' && !updated"
                      class="btn btn-secondary"
                      v-on:click="generateFL4JCode"
              >
                <i class="fab fa-java"></i>
                Java Code
              </button>
            </div>
          </div>

          <MultiLayerConfiguration
            :model='modeldataraw'
            @changed="updated=true"
            ref="mmConf"
          />

          <LayersConfiguration
            :model='modeldataraw'
            @changed="updated=true"
            ref="layerConf"
          />

          <DiagramViasualizer
            :model='modeldataraw'
          />
        </div>


        <div class="tab-pane fade" id="runningConfigs">
          <NormalizationConfiguration
            :model='modeldataraw'
          />
        </div>

        <NNTrainingTab/>

        <div class="tab-pane fade" id="deployment">
        </div>
      </div>

    </div>

    <GenerarErrorModal
      ref="errorModal"
    />
    <GeneralOutputModal
      ref="outputModal"
    />

  </div>
</template>

<script>

  import GenerarErrorModal from '../../components/modals/GenerarErrorModal';
  import GeneralOutputModal from '../../components/modals/output/GeneralOutputModal';

  /* For Normal Model Configuration*/
  import MultiLayerConfiguration from '../../components/nnmodel/multilayer/MultiLayerConfiguration';
  import LayersConfiguration from '../../components/nnmodel/multilayer/LayersConfiguration';
  import DiagramViasualizer from '../../components/nnmodel/multilayer/DiagramViasualizer';

  /* For Input Configuration */
  import NormalizationConfiguration from '../../components/nnmodel/inputs/NormalizationConfiguration';

  import NNTrainingTab from '../../components/NNTrainingTab';

  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
      GenerarErrorModal,

      NNTrainingTab,

      GeneralOutputModal,
      MultiLayerConfiguration,
      LayersConfiguration,
      DiagramViasualizer,

      NormalizationConfiguration,
    },
    mounted() {
      this.loadModelToCurrentModel(this.id);
    },
    data() {
      return {
        id: this.$route.params.id,
        updated: false,
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          modeldata: 'getCurrentModel',
        },
      ),
      modeldataraw() {
        if (this.modeldata && this.modeldata.rawModel) {
          return this.modeldata.rawModel
        } else {
          return {}
        }
      }
    },
    watch: {
    },
    methods: {
      ...mapActions('dl4j',
          {
            loadModelToCurrentModel: 'loadModelToCurrentModel',
            persistCurrentModel: 'persistCurrentModel',
            generateDL4JConfigForModel: 'generateDL4JConfigForModel',
            generateDL4JCodeForModel: 'generateDL4JCodeForModel',
          },
      ),
      saveModel() {
        let loader = this.$loading.show();
        this.persistCurrentModel(this.id)
          .then((response) => {
            this.updated = false;
            this.$refs.mmConf.setUptoDate();
            this.$refs.layerConf.setUptoDate();
            loader.hide();
            this.$notify({
                type: 'success',
                title: 'Success',
                text: 'Model configuration was saved.',
            });
          }).catch((error) => {
            console.log("---------" + error.response.data);
            this.$notify({
                type: 'error',
                title: 'Error',
                text: 'Problem saving the current configuration',
            });
            this.$refs.errorModal.openModal("Error Saving Model",error.response.data);
            loader.hide();
          });
      },
      generateFL4JConfig() {
        let loader = this.$loading.show();
        this.generateDL4JConfigForModel(this.id)
          .then((response) => {
            loader.hide();
            this.$refs.outputModal.openModal("DL4j Json Config", JSON.stringify(response, null, 2));
          }).catch((error) => {
            this.$notify({
              type: 'error',
              title: 'Error',
              text: 'Problem generating the current configuration',
            });
            this.$refs.errorModal.openModal("Error generating LD4J Config", error.response.data);
            loader.hide();
          });
      },
      generateFL4JCode() {
        let loader = this.$loading.show();
        this.generateDL4JCodeForModel(this.id)
          .then((response) => {
            loader.hide();
            this.$refs.outputModal.openModal("DL4j Model Code", response);
          }).catch((error) => {
            this.$notify({
              type: 'error',
              title: 'Error',
              text: 'Problem generating the current configuration',
            });
            this.$refs.errorModal.openModal("Error generating LD4J Code", error.response.data);
            loader.hide();
          });
      },
    }
  }
</script>
