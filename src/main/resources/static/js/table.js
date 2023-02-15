
    $(document).ready(function () {

        var books = $.getJSON( "/api/book/.json", function() {
            console.log( "success" );
        })
            .done(function() { console.log( "second success" ); })
            .fail(function() { console.log( "error" ); })
            .always(function() { console.log( "complete" ); });



            $('#table_1').DataTable({
                data: books,
                columns: [
                    { data: 'title' },
                    { data: 'author' },
                ],
                "pageLength": 3
            })
        });