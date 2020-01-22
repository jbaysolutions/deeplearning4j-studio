<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-book-reader"></i>
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
        <div class="row mb-3" v-for="(item, index) in training.rawStrategy.recordReader">
          <div class="col-12">
            <CSVRecordReaderConfig
              v-if="item.type==='CSV_RECORD_READER'"
              :position="index"
              @changed="notifyChange()"
            />
          </div>
        </div>

        <div>
          <div class="row mb-3">
            <div class="col-12 text-center">
              <button type="button" class="btn btn-primary" v-on:click="$refs.createRecordReaderModal.openModal()">
                <i class="fas fa-plus"></i>
                Add New Record Reader
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <CreateRecordReaderModal
      ref="createRecordReaderModal"
      @newItem="createRecordReader"
    />
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  import CreateRecordReaderModal from '../../../modals/CreateRecordReaderModal'
  import CSVRecordReaderConfig from './util/CSVRecordReaderConfig'


  export default {
    components: {
      CreateRecordReaderModal,
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
      createRecordReader(request) {
        this.generateCleanRecordReader(request.type)
          .then((response) => {
            response.name = request.name;
            this.training.rawStrategy.recordReader.push(response);
            this.notifyChange();
          }).catch((error) => {
            console.log('Error detected ? ' + error);
            throw error
          });
      },
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
