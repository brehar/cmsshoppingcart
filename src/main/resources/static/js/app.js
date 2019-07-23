$(() => {
  $('a.confirmDeletion').click(() => {
    if (!confirm('Are you sure?')) {
      return false;
    }
  });
});
