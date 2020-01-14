<template>
  <div class="modal fade" id="createNewLayerModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createModelModalTitle">Create New Layer</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <div class="form-group">
            <label class="control-label" for="layerTypeField">Available Layer Types</label>
            <select
              class="custom-select"
              id="layerTypeField"
              v-model="newModel.type"
            >
              <option v-for="item in helper.nnLayerTypeList" :value="item.key">{{item.description}}</option>
            </select>
          </div>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button
            :disabled="newModel.type===''"
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
          type: '',
          index: -1,
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
      openModal(index) {
        this.newModel.type = '';
        this.newModel.index = index;
        $('#createNewLayerModal').modal('show')
      },
      createNNLayer() {
        this.$emit('newLayerCreated', this.newModel);
        $('#createNewLayerModal').modal('hide')
      },
    }
  }
</script>
