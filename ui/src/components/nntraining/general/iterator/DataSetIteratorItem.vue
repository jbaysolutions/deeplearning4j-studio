<template>
  <div class="card border-primary mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong v-if="item.type==='RECORD_READER_DATA_ITERATOR'">Record Reader Data Iterator</strong>

      <!--<span v-if="item.type==='RECORD_READER_DATA_ITERATOR'" class="badge badge-pill badge-dark">Metric: {{item.metric}}</span>-->

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
              Remove Iterator
            </button>
          </div>
        </div>


        <div v-if="item.type==='RECORD_READER_DATA_ITERATOR'">
          <div class="row">
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="batchSizeField">Batch Size</label>
                  <input
                    v-model="item.batchSize"
                    v-on:change="notifyChange()"
                    class="form-control" id="batchSizeField" type="number"
                    placeholder="Size of the Batch of each iteration" >
                </fieldset>
              </div>
            </div>
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="layerTypeField">Record Reader</label>
                  <select
                    class="custom-select"
                    id="layerTypeField"
                    v-model="item.recordReader"
                  >
                    <option v-for="(item, index) in recordReaders" :value="index">{{item.name}}</option>
                  </select>
                </fieldset>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <label class="control-label">Classification</label>
            </div>
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="classificationLabelIndexField">Classification Index:</label>
                  <input
                    v-model="item.classificationLabelIndex"
                    v-on:change="notifyChange()"
                    class="form-control" id="classificationLabelIndexField" type="number"
                    placeholder="Index where labels starts" >
                </fieldset>
              </div>
            </div>
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="classificationNumClassesField">Classification Num. Classes:</label>
                  <input
                    v-model="item.classificationNumClasses"
                    v-on:change="notifyChange()"
                    class="form-control" id="classificationNumClassesField" type="number"
                    placeholder="How many classes?" >
                </fieldset>
              </div>
            </div>
            <div class="col-12">
              <label class="control-label">Regression</label>
            </div>
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="regressionLabelIndexFromField">Regression Index From:</label>
                  <input
                    v-model="item.regressionLabelIndexFrom"
                    v-on:change="notifyChange()"
                    class="form-control" id="regressionLabelIndexFromField" type="number"
                    placeholder="Index for regression from" >
                </fieldset>
              </div>
            </div>
            <div class="col-6">
              <div class="form-group">
                <fieldset>
                  <label class="control-label" for="regressionLabelIndexToField">Regression Index To:</label>
                  <input
                    v-model="item.regressionLabelIndexTo"
                    v-on:change="notifyChange()"
                    class="form-control" id="regressionLabelIndexToField" type="number"
                    placeholder="Index for regression to" >
                </fieldset>
              </div>
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
      usage: {
        type: String,
        required: true,
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
            training: 'getCurrentTrainingStrategy',
          },
      ),
      recordReaders() {
        return this.training.rawStrategy.recordReader;
      }
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      notifyChange() {
        this.$emit('changed');
      },
      notifyDelete() {
        this.$emit('toDelete', this.usage);
      }
    }

  }
</script>
