<template>
  <div class="card border-primary mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong>Normalizer Standardize</strong>
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
              Remove Preprocessor
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <fieldset>
              <label class="control-label" for="layerTypeField">Use for Fit?</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="normalizer.useTrainingDataForFit"
                v-on:change="notifyChange()"
              >
                <option value="TRAINING_DS">Training Dataset</option>
                <option value="TESTING_DS">Testing Dataset</option>
                <!--<option value="BOTH">Both</option>-->
              </select>
            </fieldset>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    components: {
    },
    mounted() {
    },
    props: {
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: false,
      }
    },
    watch: {
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
      normalizer() {
        return this.training.rawStrategy.preprocessorNormalizerWrapper;
      },
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanInputSplit: 'generateCleanInputSplit',
        },
      ),
      notifyChange() {
        this.$emit('changed');
      },
      notifyDelete() {
        this.$emit('toDelete', this.usage);
      },
      setInputSplit(event) {
        /*if(event.target.value!=='') {
          console.log("---------> " + event.target.value);
          this.generateCleanInputSplit(event.target.value)
            .then((response) => {
              this.reader.inputSplit = response;
              this.spliter = this.reader.inputSplit.type;
              this.notifyChange();
            }).catch((error) => {
              console.log('Error detected ? ' + error);
              throw error
            })
        } else {
          this.reader.inputSplit = null;
          this.notifyChange();
        }*/
      },
      generateID() {
        return (Math.random() + "").replace("0.", "AAA");
      },
    }
  }
</script>
