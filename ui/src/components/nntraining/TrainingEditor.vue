<template>
  <div class="row mt-3">
    <div class="col-12">
      <div class="row">
        <div class="col">
        </div>
        <div class="col">
          <span v-if="updated" class="badge badge-danger">Training strategy was modified, requires saving</span>
        </div>
        <div class="col">
          <div v-if="updated" class="text-right">
            <button class="btn btn-primary" v-on:click="saveModel">
              Save
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="training && training.rawStrategy && training.rawStrategy.trainingType==='LOCAL'" class="col-12 mt-3">
      <RecordReaderConfiguration
        @changed="updated=true"
      />
      <EarlyStoppingTrainerConfig
        @changed="updated=true"
      />

      <div class="card border-primary mb-3" >
        <div class="card-header">
          <i class="fas fa-running"></i>
          Perform Training
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-12">
              <label class="control-label" >Training Data</label>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <label class="control-label" >Testing Data</label>
            </div>
          </div>
        </div>
      </div>

    </div>

    <div v-if="training && training.rawStrategy && training.rawStrategy.trainingType==='SPARK'" class="col-12 mt-3">
      SPARK NOT IMPLEMENTED YET
    </div>

    <GenerarErrorModal
      ref="errorModal"
    />
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import RecordReaderConfiguration from '../nntraining/local/recordReaders/RecordReaderConfiguration'
  import EarlyStoppingTrainerConfig from '../nntraining/local/trainer/EarlyStoppingTrainerConfig'
  import GenerarErrorModal from '../../components/modals/GenerarErrorModal';

  export default {
    components: {
      RecordReaderConfiguration,
      EarlyStoppingTrainerConfig,

      GenerarErrorModal,
    },
    mounted() {
    },
    computed: {
      ...mapGetters('dl4j',
        {
          modeldata: 'getCurrentModel',
          training: 'getCurrentTrainingStrategy',
        },
      ),
    },
    data() {
      return {
        updated: false,
      }
    },
    methods: {
      ...mapActions('dl4j',
          {
            persistCurrentTrainingStrategy: 'persistCurrentTrainingStrategy',
          },
      ),
      saveModel() {
        let loader = this.$loading.show();
        this.persistCurrentTrainingStrategy(this.training.strategyId)
          .then((response) => {
            this.updated = false;
            loader.hide();
            this.$notify({
                type: 'success',
                title: 'Success',
                text: 'Training Strategy configuration was saved.',
            });
          }).catch((error) => {
            console.log("---------" + error.response.data);
            this.$notify({
                type: 'error',
                title: 'Error',
                text: 'Problem saving the current configuration',
            });
            this.$refs.errorModal.openModal("Error Saving Training Strategy",error.response.data);
            loader.hide();
          });
      }
    }
  }
</script>
