$(function() {
   $('form.form-ajax').submit((e) => {
       e.preventDefault();

       const load = $("#loading");

       const form = $(e.target);

       load.fadeIn(200).css("display", "flex");
       $.post({
           url: form.attr("action"),
           dataType: "json",
           data: form.serialize(),
           success: function (response) {
               load.fadeOut(200);
               if (response.error === true) {
                   Dialog.response({
                       type: "warning",
                       message: response.message
                   })
                   load.fadeOut(200);
                   return;
               }

               if (typeof response.alert !== "undefined") {
                   Dialog.alert(response.alert);
               }

               if (typeof response.redirect !== "undefined") {
                   window.location.href = response.redirect;
               }
           },
           error: function (request, status, error) {
               Dialog.response({type: 'danger'});
               load.fadeOut();
           },
       });
   });
});