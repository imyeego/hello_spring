(function () {

    window.http = function () {
        fetch("http://localhost:8080/customers")
            .then(function (response) {
                if (!response.ok){
                    throw new Error("HTTP error, status = " + response.status);
                }
                return response.json();
            })
            .then(function (json) {
                for (var i = 0; i < json.length; i ++){
                    var customer = json[i];
                    log(customer.name);
                }
            })
            .catch(function (error){ log(error.message) });
    };


    window.post_json = function () {
        fetch("http://localhost:8080/customer_id", {
            method : 'post',
            headers : {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify({id : 3, name : '愤青'})
            })
            .then(function (res) {
                if (!res.ok){
                    throw new Error("HTTP error, status = " + res.status);
                }
                return res.json();
            })
            .then(function (json) {
                log(json);
            })
            .catch(function (error) {
                log(error.message);
            });

    };


    window.post_form = function () {
        let url = "http://localhost:8080/customer_name";

        let form = new URLSearchParams();
        form.set('id', '4');
        form.set('name', '咸鱼');

        fetch(url, {
            method : 'post',
            body : form
            })
            .then(res => {
                if (!res.ok){
                    throw new Error("HTTP error, status = " + res.status);
                }
                return res.json();
            })
            .then(json => {
                log(json);
            })
            .catch(error => {
                log(error.message);
            });
    };

    window.upload_file = function (file) {
        log(file);
        let url = "http://localhost:8080/upload";
        let form = new FormData();

        form.append('file', file);

        fetch(url, {
            method : 'post',
            body : form
            })
            .then(res => {
                if (!res.ok){
                    throw new Error("HTTP error, status = " + res.status);
                }
                return res.json();
            })
            .then(json => {
                log(json);
            })
            .catch(error => {
                log(error.message);
            });


    };

    window.upload_file_base64 = function(file){
        let url = "http://localhost:8080/upload_base64";

        img2base64(file).then(data => {
            let form = new FormData();
            form.append('base64', data);
                return form;
            })
            .then(form => {
                return fetch(url, {
                    method : 'post',
                    body : form
                })
            })
            .then(res => {
                if (!res.ok){
                    throw new Error("HTTP error, status = " + res.status);
                }
                return res.json();
            })
            .then(json => {
                log(json);
            })
            .catch(error => {
                log(error.message);
            });
    };

    function img2base64(file) {
        const promise = new Promise(function (resolve, reject) {
            const reader = new FileReader();
            reader.onloadend = () => resolve(reader.result);
            reader.onerror = reject;
            reader.readAsDataURL(file)
        });
        return promise;
    }


    function log(msg) {
        console.log(msg);
    }


}).call(this);