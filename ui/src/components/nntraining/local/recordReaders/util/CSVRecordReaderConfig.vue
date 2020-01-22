<template>
  <div class="card border-primary mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong v-if="reader.type==='CSV_RECORD_READER'">Name: {{reader.name}}</strong>


      <span v-if="reader.type==='CSV_RECORD_READER'" class="badge badge-pill badge-primary">CSV Record Reader</span>

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
          <div class="col-6">
            <div class="form-group">
              <label for="nameField">Record Reader Name:</label>
              <input v-model="reader.name"
                     v-on:change="notifyChange()"
                     type="text"
                     class="form-control" id="nameField"
                     aria-describedby="exampleCellDelimiterHelp"
                     placeholder="Name of the Record Reader">
              <small id="nameFieldHelp" class="form-text text-muted">
                A name for you to identify the Record Reader on the Configuration
              </small>
            </div>
          </div>
          <div class="col-6 text-right">
            <button type="button" class="btn btn-danger btn-sm"
                    v-on:click="notifyDelete()"
            >
              <i class="fas fa-trash-alt"></i>
              Delete Record Reader
            </button>
          </div>
        </div>


              <div class="row">
                <div class="col-4">
                  <fieldset>
                    <div class="form-group">
                      <label for="exampleCellDelimiter">Cell Delimiter</label>
                      <input v-model="reader.delimiter"
                             v-on:change="notifyChange()"
                             type="text"
                             maxlength="1"
                             class="form-control" id="exampleCellDelimiter"
                             aria-describedby="exampleCellDelimiterHelp"
                             placeholder="Cell delimiter char">
                      <small id="exampleCellDelimiterHelp" class="form-text text-muted">
                        Character used as a Cell Delimiter. Only one Char!
                      </small>
                    </div>
                  </fieldset>
                </div>
                <div class="col-4">
                  <fieldset>
                    <div class="form-group">
                      <label for="exampleQuoteDelimiter">Quote Delimiter</label>
                      <input v-model="reader.quote"
                             v-on:change="notifyChange()"
                             type="text"
                             maxlength="1"
                             class="form-control" id="exampleQuoteDelimiter"
                             aria-describedby="exampleQuoteDelimiterHelp"
                             placeholder="Quote delimiter char">
                      <small id="exampleQuoteDelimiterHelp" class="form-text text-muted">Character used as a Text Delimiter. Only
                        one Char!
                      </small>
                    </div>
                  </fieldset>
                </div>
                <div class="col-4">
                  <fieldset>
                    <div class="form-group">
                      <label for="inputSkipLines">Skip Lines</label>
                      <input v-model="reader.skipLines"
                             v-on:change="notifyChange()"
                             type="number"
                             maxlength="1"
                             class="form-control" id="inputSkipLines"
                             aria-describedby="inputSkipLinesHelp"
                             placeholder="0">
                      <small id="inputSkipLinesHelp" class="form-text text-muted">Number of lines to skip from the CSV (the header, for example)
                      </small>
                    </div>
                  </fieldset>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="card border-primary mb-3">

                    <div class="card-header" data-toggle="collapse" :href="'#'+initializerId"  v-on:click="initializerOpen=!initializerOpen" style="cursor: pointer">
                      Initializing CSV Record Reader
                      <button
                        type="button"
                        class="close"
                        data-toggle="collapse"
                        :href="'#'+initializerId"
                        v-on:click="initializerOpen=!initializerOpen"
                      >
                        <i v-if="initializerOpen" class="fas fa-minus-square"></i>
                        <i v-else class="fas fa-plus-square"></i>
                      </button>
                    </div>
                    <div class="collapse" :id="initializerId">
                      <div class="card-body">
                        <div class="row">
                          <div class="col-6">
                            <div class="form-group">
                              <label for="inputSplitSelector">Select Input Split</label>
                              <select class="form-control" id="inputSplitSelector" v-on:change="setInputSplit" v-model="spliter">
                                <option value=""></option>
                                <option value="FILE_SPLIT">File Split</option>
                              </select>
                            </div>
                          </div>
                        </div>

                        <FileSplitConfig
                          v-if="reader && reader.inputSplit && reader.inputSplit.type==='FILE_SPLIT'"
                          :position="position"
                          @changed="notifyChange()"
                          />
                      </div>
                    </div>

                  </div>
                </div>
              </div>


      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import FileSplitConfig from '../../inputSplit/FileSplitConfig'

  export default {
    components: {
      FileSplitConfig,
    },
    mounted() {
    },
    props: {
      position: {
        type: Number,
        required: true,
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: false,
        initializerId: this.generateID(),
        initializerOpen: false,
        spliter: '',
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
      reader() {
        if (this.training && this.training.rawStrategy && this.training.rawStrategy.recordReader) {
          if (this.training.rawStrategy.recordReader[this.position].inputSplit) {
            this.spliter = this.training.rawStrategy.recordReader[this.position].inputSplit.type;
          }
          return this.training.rawStrategy.recordReader[this.position];
        } else {
          this.spliter = '';
          return {
            delimiter: 'ERROR',
            quote: 'ERROR',
            skipLines: -1,
          };
        }
      },
    },
    methods: {
      ...mapActions('dl4j',
        {
          generateCleanInputSplit: 'generateCleanInputSplit',
        },
      ),
      executeSetterSpliter() {
        if (this.reader.inputSplit) {
          this.spliter = this.reader.inputSplit.type;
        }
        this.spliter = '';
      },
      notifyChange() {
        this.$emit('changed');
      },
      setInputSplit(event) {
        if(event.target.value!=='') {
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
        }
      },
      generateID() {
        return (Math.random() + "").replace("0.", "AAA");
      },
    }
  }
</script>
