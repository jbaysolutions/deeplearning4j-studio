<template>
  <div class="modal fade" id="createGeneralOutputModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header bg-primary ">
          <h5 class="modal-title" id="createModelModalTitle">{{title}}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <textarea ref="outputTextFieldForCopy" class="testCodeArea" rows="20" :disabled="flag">{{message}}</textarea>
          <p></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click="copyToClipboard"><i class="far fa-copy"></i> Copy to Clipboard</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
  .testCodeArea {
    width: 100%;
    overflow-y: scroll;
    overflow-x: scroll;
    overflow:-moz-scrollbars-vertical;
  }
</style>
<script>
  export default {
    data() {
      return {
        message: '',
        title: 'Output',
        error: false,
        flag: true,
      }
    },
    computed: {
    },
    methods: {
      copyToClipboard() {

        this.flag = false;
        this.$nextTick(() => {
          this.$refs.outputTextFieldForCopy.select();
          document.execCommand('copy');
          this.flag = true;
        });
      },
      openModal(message) {
        this.message = message;
        $('#createGeneralOutputModal').modal('show')
      },
      openModal(title, message) {
        this.title = title;
        this.message = message;
        $('#createGeneralOutputModal').modal('show')
      },
    }
  }
</script>
