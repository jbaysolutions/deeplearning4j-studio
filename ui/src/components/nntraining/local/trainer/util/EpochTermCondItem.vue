<template>
  <div class="card border-primary mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong v-if="item.type==='MAX_EPOCHS'">Maximum Number of Epochs Condition</strong>
      <strong v-if="item.type==='SCORE_IMPROVEMENT'">Score Improvements</strong>
      <strong v-if="item.type==='BEST_SCORE'">Best Expected Score Achieved</strong>


      <span v-if="item.type==='MAX_EPOCHS'" class="badge badge-pill badge-dark">Max. Epochs: {{item.maxEpochs}}</span>

      <span v-if="item.type==='SCORE_IMPROVEMENT'" class="badge badge-pill badge-dark">Max. Epochs Without Improv: {{item.maxEpochsWithNoImprovement}}</span>
      <span v-if="item.type==='SCORE_IMPROVEMENT'" class="badge badge-pill badge-dark">Min. Improv. : {{item.minImprovement}}</span>

      <span v-if="item.type==='BEST_SCORE'" class="badge badge-pill badge-dark">Best Score: {{item.bestExpectedScore}}</span>

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
          <div class="col-12 text-right">
            <button type="button" class="btn btn-danger btn-sm"
                    v-on:click="notifyDelete()"
            >
              <i class="fas fa-trash-alt"></i>
              Delete Condition
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-6">
            <div class="form-group">
              <fieldset v-if="item.type==='MAX_EPOCHS'">
                <label class="control-label" for="maxcEpochsField">Maximum number of Epochs</label>
                <input
                  v-model="item.maxEpochs"
                  v-on:change="notifyChange()"
                  class="form-control" id="maxcEpochsField" type="number"
                  placeholder="The maximum number of epochs for the training" >
              </fieldset>

              <fieldset v-if="item.type==='SCORE_IMPROVEMENT'">
                <label class="control-label" for="maxEpochsWImpField">Maximum Epochs Without Improvement</label>
                <input
                  v-model="item.maxEpochsWithNoImprovement"
                  v-on:change="notifyChange()"
                  class="form-control" id="maxEpochsWImpField" type="number"
                  placeholder="The maximum number of epochs without noticeable improvement " >
              </fieldset>

              <fieldset v-if="item.type==='BEST_SCORE'">
                <label class="control-label" for="bestExpectedScoreField">Best Expected Score</label>
                <input
                  v-model="item.bestExpectedScore"
                  v-on:change="notifyChange()"
                  class="form-control" id="bestExpectedScoreField" type="number"
                  placeholder="The Best expected score threshold to stop." >
              </fieldset>
            </div>
          </div>
          <div class="col-6">
            <div class="form-group">
              <fieldset v-if="item.type==='SCORE_IMPROVEMENT'">
                <label class="control-label" for="minImprovementField">Min. Improvement Threshold</label>
                <input
                  v-model="item.minImprovement"
                  v-on:change="notifyChange()"
                  class="form-control" id="minImprovementField" type="number"
                  placeholder="The threshold score value for no improvement " >
              </fieldset>
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
      item: {
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
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      notifyChange() {
        this.$emit('changed');
      },
      notifyDelete() {
        this.$emit('toDelete', this.index);
      }
    }

  }
</script>
