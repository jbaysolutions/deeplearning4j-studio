<template>
  <div class="card border-primary mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong v-if="item.type==='MAX_SCORE'">Maximum Score Condition</strong>

      <span v-if="item.type==='MAX_SCORE'" class="badge badge-pill badge-dark">Score: {{item.score}}</span>

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
            <button type="button" class="btn btn-primary btn-sm"
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
              <fieldset v-if="item.type==='MAX_SCORE'">
                <label class="control-label" for="maxScoreField">Maximum Expected Score</label>
                <input
                  v-model="item.score"
                  v-on:change="notifyChange()"
                  class="form-control" id="maxScoreField" type="number"
                  placeholder="The maximum score for iteration to stop" >
              </fieldset>
            </div>
          </div>
          <div class="col-6">
            <div class="form-group">
              <!--<fieldset v-if="item.type==='MAX_SCORE'">
                <label class="control-label" for="minImprovementField">Min. Improvement Threshold</label>
                <input
                  v-model="item.minImprovement"
                  v-on:change="notifyChange()"
                  class="form-control" id="minImprovementField" type="number"
                  placeholder="The threshold score value for no improvement " >
              </fieldset>-->
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
