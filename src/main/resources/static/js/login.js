var main = {

    init : function () {

        var _this = this;
        $('#loginBtn').on('click', function () {

            _this.save();
        })
    },
    save : function () {
        var data = {
            email: $('#email').val(),
            pwd: $('#pwd').val()
        };

        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).error(function () {

            alert("다시 로그인해주세요");
        });
    }
}