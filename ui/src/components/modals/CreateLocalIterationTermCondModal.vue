<template>
  <div class="modal fade" id="createNewLocalIterationTemCondModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createModelModalTitle">Create Iteration Termination Condition</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <div class="form-group">
            <label class="control-label" for="layerTypeField">Available Condition Types</label>
            <select
              class="custom-select"
              id="layerTypeField"
              v-model="newModel.type"
            >
              <option v-for="item in helper.localIterationTerminationCondition" :value="item.key">{{item.description}}</option>
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
        this.newModel.type = '';
        $('#createNewLocalIterationTemCondModal').modal('show')
      },
      createNNLayer() {
        this.$emit('newItem', this.newModel);
        $('#createNewLocalIterationTemCondModal').modal('hide')
      },
    }
  }
</script>
