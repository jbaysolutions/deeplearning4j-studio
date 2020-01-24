<template>
  <div class="card border-dark mb-3">
    <div class="card-header">
      <i class="fas fa-running"></i>
      Perform Training
    </div>
    <div class="card-body">
      <div class="row">
        <div class="col-12">
          <!-- for next stuff -->
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <label class="control-label">Training Data</label>
        </div>
        <div v-if="!trainer.trainingDataIterator" class="col-12 text-center">
          <button type="button" class="btn btn-primary btn-sm" v-on:click="$refs.addDataIterator.openModal('TRAINING')">
            <i class="fas fa-plus"></i>
            Set Training Data Reader
          </button>
        </div>
        <div class="col-12" v-else>
          <DataSetIteratorItem
            :item="trainer.trainingDataIterator"
            usage="TRAINING"
            @toDelete="deleteIterator"
            @changed="notifyChange"
          />
        </div>

      </div>
      <div class="row">
        <div class="col-12">
          <label class="control-label">Testing Data</label>
        </div>
        <div v-if="!trainer.testingDataIterator" class="col-12 text-center">
          <button type="button" class="btn btn-primary btn-sm" v-on:click="$refs.addDataIterator.openModal('TESTING')">
            <i class="fas fa-plus"></i>
            Set Testing Data Reader
          </button>
        </div>
        <div class="col-12" v-else>
          <DataSetIteratorItem
            :item="trainer.testingDataIterator"
            usage="TESTING"
            @toDelete="deleteIterator"
            @changed="notifyChange"
          />
        </div>

      </div>

      <div class="row" v-if="trainer.testingDataIterator && trainer.trainingDataIterator">
        <div class="col-12 text-center mt-3">
          <button type="button" class="btn btn-success btn-sm" v-on:click="executeTrainingStrategy(training.strategyId)">
            <i class="fas fa-play"></i>
            Execute Training
          </button>
        </div>
      </div>
    </div>
    <CreateLocalDataIteratorModal
      ref="addDataIterator"
      @newItem="createDataIterator"
    />
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  import CreateLocalDataIteratorModal from '../../../modals/CreateLocalDataIteratorModal'
  import DataSetIteratorItem from '../../general/iterator/DataSetIteratorItem'

  export default {
    components: {
      CreateLocalDataIteratorModal,
      DataSetIteratorItem,
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
      trainer() {
        if (this.training && this.training.rawStrategy) {
          return this.training.rawStrategy;
        } else {
          return {

          };
        }
      }
    },
    data() {
      return {
      }
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanLocalDatasetIterator: 'generateCleanLocalDatasetIterator',
          executeTrainingStrategy: 'executeTrainingStrategy',
        }
      ),
      createDataIterator(item) {
        console.log("CREATE DATA SET ITERATOR : " + item.usage + " and " + item.type);
        this.generateCleanLocalDatasetIterator(item.type)
          .then((response) => {
            if (item.usage==='TRAINING') {
              this.trainer.trainingDataIterator = response;
              this.notifyChange();
            }
            if (item.usage==='TESTING') {
              this.trainer.testingDataIterator = response;
              this.notifyChange();
            }

          }).catch((error) => {
            console.log('Error detected ? ' + error);
          })
      },
      deleteIterator(usage) {
        if (usage==='TRAINING') {
          this.trainer.trainingDataIterator = null;
          this.notifyChange();
        }
        if (usage==='TESTING') {
          this.trainer.testingDataIterator = null;
          this.notifyChange();
        }
      },
      notifyChange() {
        this.$emit('changed');
      },
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
