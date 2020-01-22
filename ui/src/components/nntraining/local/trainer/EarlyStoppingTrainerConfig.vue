<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-graduation-cap"></i>
      Early Stopping Trainer Configuration
      <button
              type="button"
              class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId" >
      <div class="card-body">

        <div class="row mb-3">
          <div class="col-12">
            <h6 style="font-weight: bold;">Epoch Termination Conditions</h6>
          </div>

          <div class="col-12"
               v-if="trainer.terminationConditionList && trainer.terminationConditionList.length > 0"
               v-for="(item, index) in trainer.terminationConditionList "
          >
            <EpochTermCondItem
              :item="item"
              :index="index"
              @toDelete="deleteEpochCondition"
              @changed="notifyChange"
            />
          </div>

          <div class="col-12 text-center" v-if="trainer.terminationConditionList">
            <button type="button" class="btn btn-primary btn-sm" v-on:click="$refs.createEpochTermCond.openModal()">
              <i class="fas fa-plus"></i>
              Add Epoch Termination Condition
            </button>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-12">
            <h6 style="font-weight: bold;">Iteration Termination Conditions</h6>
          </div>

          <div class="col-12 mt-3"
               v-if="trainer.iterationTerminationConditionList && trainer.iterationTerminationConditionList.length > 0"
               v-for="(item, index) in trainer.iterationTerminationConditionList "
          >
            <IterationTermCondItem
              :item="item"
              :index="index"
              @toDelete="deleteIterationCondition"
              @changed="notifyChange"
            />
          </div>

          <div class="col-12 text-center" v-if="trainer.iterationTerminationConditionList">
            <button type="button" class="btn btn-primary btn-sm" v-on:click="$refs.createIterationTermCond.openModal()">
              <i class="fas fa-plus"></i>
              Add Iteration Termination Condition
            </button>
          </div>
        </div>


      </div>
    </div>
    <CreateLocalEpochTermCondModal
      ref="createEpochTermCond"
      @newItem="createEpochTermCond"
    />
    <CreateLocalIterationTermCondModal
      ref="createIterationTermCond"
      @newItem="createIterationTermCond"
    />
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import CreateLocalEpochTermCondModal from '../../../modals/CreateLocalEpochTermCondModal'
  import CreateLocalIterationTermCondModal from '../../../modals/CreateLocalIterationTermCondModal'
  import EpochTermCondItem from './util/EpochTermCondItem'
  import IterationTermCondItem from './util/IterationTermCondItem'

  export default {
    components: {
      CreateLocalEpochTermCondModal,
      CreateLocalIterationTermCondModal,
      EpochTermCondItem,
      IterationTermCondItem,
    },
    mounted() {
    },
    data() {
      return {
        opened: false,
        widgetId: this.generateID(),
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
      trainer() {
        if (this.training && this.training.rawStrategy && this.training.rawStrategy.earlyStoppingTrainer) {
          return this.training.rawStrategy.earlyStoppingTrainer;
        } else {
          return {

          };
        }
      }
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanLocalEpochTermCodition: 'generateCleanLocalEpochTermCodition',
          generateCleanLocalIterationTermCodition: 'generateCleanLocalIterationTermCodition',
        },
      ),
      createEpochTermCond(item) {
        console.log("CALL TO CREATE EPOCH: " + item.type);
        this.generateCleanLocalEpochTermCodition(item.type)
          .then((response) => {
            this.trainer.terminationConditionList.push(response);
            this.notifyChange();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
          });
      },
      createIterationTermCond(item) {
        console.log("CALL TO CREATE IT: " + item.type);
        this.generateCleanLocalIterationTermCodition(item.type)
          .then((response) => {
            this.trainer.iterationTerminationConditionList.push(response);
            this.notifyChange();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
          });
      },
      deleteEpochCondition(index) {
        console.log("DELETE EPOCH : " + index);
        this.trainer.terminationConditionList.splice(index,1);
        this.notifyChange();
      },
      deleteIterationCondition(index) {
        console.log("DELETE ITERATION : " + index);
        this.trainer.iterationTerminationConditionList.splice(index,1);
        this.notifyChange();
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
