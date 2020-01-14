<template>
  <div class="modal fade" id="createErrorModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header bg-danger ">
          <h5 class="modal-title" id="createModelModalTitle">{{title}}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <p>{{message}}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        message: '',
        title: 'Error',
        error: false,
      }
    },
    computed: {
    },
    methods: {
      openModal(message) {
        this.message = message;
        $('#createErrorModal').modal('show')
      },
      openModal(title, message) {
        this.title = title;
        this.message = message;
        $('#createErrorModal').modal('show')
      },
    }
  }
</script>
