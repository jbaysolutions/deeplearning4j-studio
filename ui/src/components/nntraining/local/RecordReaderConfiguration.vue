<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-cogs"></i>
      Record Reader Configuration
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
        <!-- -------------------- SELECT WHICH RECORD READER --------------------------- -->
        <div class="row">
          <div class="col-6">
            <div v-if="training.rawStrategy.recordReader===null"
              class="form-group">
              <fieldset>
                <label class="control-label" for="recordReader">Record Reader Type</label>
                <div class="form-group">
                  <select class="form-control" id="recordReader" v-on:change="setRecordReader">
                    <option value=""></option>
                    <option value="CSV_RECORD_READER">CSV Record Reader</option>
                    <!--<option value="CSV_REGEX_RECORD_READER">CSV Regex Record Reader</option>-->
                  </select>
                </div>
              </fieldset>
            </div>
            <div v-else
              class="form-group">
              <fieldset>
                <label class="control-label" for="recordReader">Record Reader Type</label>
                <div class="form-group">
                  <select class="form-control" id="recordReader" v-on:change="setRecordReader" v-model="training.rawStrategy.recordReader.type">
                    <option value="CSV_RECORD_READER">CSV Record Reader</option>
                    <!--<option value="CSV_REGEX_RECORD_READER">CSV Regex Record Reader</option>-->
                  </select>
                </div>
              </fieldset>
            </div>
          </div>
          <div class="col-6">
          </div>
        </div>
        <!-- -------------------- SELECT WHICH RECORD READER --------------------------- -->

        <CSVRecordReaderConfig
          v-if="training.rawStrategy &&  training.rawStrategy.recordReader && training.rawStrategy.recordReader.type==='CSV_RECORD_READER'"
          @changed="notifyChange()"
        />
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import CSVRecordReaderConfig from './recordReaders/CSVRecordReaderConfig'


  export default {
    components: {
      CSVRecordReaderConfig,
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
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanRecordReader: 'generateCleanRecordReader',
        },
      ),
      notifyChange() {
        this.$emit('changed');
      },
      setRecordReader(event) {
        if(event.target.value!=='') {
          console.log("---------> " + event.target.value);
          this.generateCleanRecordReader(event.target.value)
            .then((response) => {
              this.training.rawStrategy.recordReader = response;
              this.notifyChange();
            }).catch((error) => {
              console.log('Error detected ? ' + error);
              throw error
            })
        }
      },
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
