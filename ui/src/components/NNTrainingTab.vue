<template>
  <div class="tab-pane fade" id="training">

    <div v-if="modeldata.status!=='DRAFT' && this.trainingId===null" class="row">
      <div class="col-12 text-right">
        <p class="bs-component">
          <button type="button" class="btn btn-primary btn-sm"
                  v-on:click="openCreateTrainingModal"
          >
            <i class="fas fa-plus"></i>
            New Training Configuration
          </button>
        </p>
      </div>
      <CreateTrainingConfigModal
        ref="createTrainingModal"
        @newTrainingCreated="reloadData"
      />
    </div>

    <!-- Show Table Of Strategies List-->
    <div v-if="this.trainingId===null" class="row">
      <div class="col-12">
        <table class="table table-dark table-striped">
          <thead>
          <tr class="table-primary">
            <th scope="col">#</th>
            <th scope="col">Training Strategy Name</th>
            <th scope="col">Type</th>
            <th scope="col">Status</th>
            <th scope="col"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(trainset, index) in modeldata.trainingStrategyList">
            <th scope="row">{{index+1}}</th>
            <td>{{trainset.name}}</td>
            <td>{{trainset.rawStrategy.trainingType}}</td>
            <td>{{trainset.status}}</td>
            <td width="1">
              <div class="btn-group" role="group" aria-label="Basic example">
                <button class="btn btn-primary btn-sm"
                        style="white-space: nowrap;"
                        v-on:click="openTrainingStrategy(trainset)">
                  <i class="fab fa-codepen"></i>
                  Open
                </button>
                <button type="button"
                        v-on:click="openDeleteModal(trainset.strategyId, trainset.name)"
                        class="btn btn-danger btn-sm" style="white-space: nowrap;">
                  <i class="fas fa-trash"></i>
                  Delete
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-else >
      <div class="row">
        <div class="col-12">
          <h3>
            <button v-on:click="unselectTraining"
                    class="btn btn-primary btn-sm">
              <i class="fas fa-chevron-left"></i>
            </button>
            Training Strategy: <b>{{training.name}}</b></h3>
        </div>
      </div>

      <TrainingEditor/>

    </div>

    <DeleteTrainingStrategyModal
      ref="deleteTrainingModal"
      @modelDeleted="reloadData"
    />
  </div>
</template>

<script>

  import CreateTrainingConfigModal from '../components/modals/CreateTrainingConfigModal';
  import DeleteTrainingStrategyModal from '../components/modals/DeleteTrainingStrategyModal'
  import TrainingEditor from '../components/nntraining/TrainingEditor'

  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
      CreateTrainingConfigModal,
      DeleteTrainingStrategyModal,
      TrainingEditor
    },
    props: {
    },
    mounted() {},
    data() {
      return {
        trainingId: null,
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          modeldata: 'getCurrentModel',
          training: 'getCurrentTrainingStrategy',
        },
      ),
    },
    methods: {
      ...mapActions('dl4j',
        {
          loadModelToCurrentModel: 'loadModelToCurrentModel',
          getTrainingStrategy: 'getTrainingStrategy',
        }
      ),
      openTrainingStrategy(trainingStrat) {
        this.trainingId = trainingStrat.strategyId;
        this.getTrainingStrategy(trainingStrat.strategyId);
      },
      openCreateTrainingModal() {
        this.$refs.createTrainingModal.openModal(this.modeldata.modelId);
      },
      openDeleteModal(strategyId, strategyName) {
        this.$refs.deleteTrainingModal.openModal(strategyId, strategyName)
      },
      unselectTraining() {
        this.trainingId = null;
      },
      reloadData() {
        this.loadModelToCurrentModel(this.modeldata.modelId);
      },
    }
  }

</script>
