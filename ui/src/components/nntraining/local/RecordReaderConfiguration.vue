<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-cogs"></i>
      Record Reader List
      <span class="badge badge-pill badge-dark">{{training.rawStrategy.recordReader.length}} Record Reads</span>
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

        <div v-if="training.rawStrategy.recordReader.length > 0 ">
          <div v-for="(item, index) in training.rawStrategy.recordReader">

            <div class="row">
              <div class="col-6">
                <div class="form-group">
                  <fieldset>
                    <label class="control-label" for="recordReader">Record Reader Type</label>
                    <div class="form-group">
                      <select class="form-control" id="recordReader" v-on:change="setRecordReader" v-model="item.type">
                        <option value="CSV_RECORD_READER">CSV Record Reader</option>
                        <!--<option value="CSV_REGEX_RECORD_READER">CSV Regex Record Reader</option>-->
                      </select>
                    </div>
                  </fieldset>
                </div>
              </div>
              <div class="col-6">
                <fieldset>
                  <label class="control-label" for="recordReader">Used For:</label>
                  <div class="form-group">
                    <select class="form-control" id="recordReader" v-on:change="notifyChange" v-model="item.usedFor">
                      <option value="TRAINING">Training Only</option>
                      <option value="TESTING">Testing Only</option>
                      <option value="BOTH">Both Training and Testing</option>
                      <!--<option value="CSV_REGEX_RECORD_READER">CSV Regex Record Reader</option>-->
                    </select>
                  </div>
                </fieldset>
              </div>
            </div>
            <CSVRecordReaderConfig
              v-if="item.type==='CSV_RECORD_READER'"
              :position="index"
              @changed="notifyChange()"
            />

          </div>
        </div>


        <div v-else>
          <div class="row mb-3">
            <div class="col-12 text-center">
              <button type="button" class="btn btn-primary" v-on:click="addNewRecordReader(0)">
                <i class="fas fa-plus"></i>
              </button>
            </div>
          </div>
        </div>
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
      addNewRecordReader(position) {
        this.generateCleanRecordReader("CSV_RECORD_READER")
          .then((response) => {
            this.training.rawStrategy.recordReader.push(response);
            this.notifyChange();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
            throw error
          });
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
