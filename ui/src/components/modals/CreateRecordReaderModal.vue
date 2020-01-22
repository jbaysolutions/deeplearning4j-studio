<template>
  <div class="modal fade" id="createLocalScoreCalculatorModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createModelModalTitle">Create Record Reader</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <div class="form-group">

            <fieldset>
              <label class="control-label" for="layerTypeField">Available Record Reader Types</label>
              <select
                class="custom-select"
                id="layerTypeField"
                v-model="newModel.type"
              >
                <option v-for="item in helper.localRecordReaderTypes" :value="item.key">{{item.description}}</option>
              </select>
            </fieldset>

            <fieldset>
               <label class="control-label" for="nameRRField">Name of the Record Reader</label>
               <input
                 v-model="newModel.name"
                 class="form-control" id="nameRRField" type="text"
                 placeholder="Name so you can identify the Record Reader" >
            </fieldset>

          </div>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button
            :disabled="newModel.type==='' || newModel.name===''"
            type="button"
            class="btn btn-primary"
            v-on:click="createNNLayer">Create</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    data() {
      return {
        newModel: {
          name: '',
          type: '',
        },
        error: false,
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
      openModal() {
        this.newModel.name = '';
        this.newModel.type = '';
        $('#createLocalScoreCalculatorModal').modal('show')
      },
      createNNLayer() {
        this.$emit('newItem', this.newModel);
        $('#createLocalScoreCalculatorModal').modal('hide')
      },
    }
  }
</script>
