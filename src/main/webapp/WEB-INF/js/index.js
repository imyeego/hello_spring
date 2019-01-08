(function () {

    window.http = function () {
        fetch("http://localhost:8080/customers")
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("HTTP error, status = " + response.status);
                }
                return response.json();
            })
            .then(function (json) {
                for (var i = 0; i < json.length; i++) {
                    var customer = json[i];
                    log(customer.name);
                }
            })
            .catch(function (error) {
                log(error.message)
            });
    };


    window.post_json = function () {
        fetch("http://localhost:8080/customer_id", {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({id: 3, name: '愤青'})
        })
            .then(function (res) {
                if (!res.ok) {
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

    window.getUserByName = function (name) {
        const url = "http://localhost:8080/user/" + name;
        const token = localStorage.getItem('token');
        fetch(url, {
            method : "get",
            headers : {
                'Authorization' : token
            }
        }).then(res => {
            if (!res.ok){
                throw new Error("HTTP error, status = " + res.status);
            }
            return res.json();
        }).then(json => {
            log(json);
        }).catch(error => {
            log(error);
        });
    };


    window.post_form = function () {
        let url = "http://localhost:8080/customer_name";

        let form = new URLSearchParams();
        form.set('id', '4');
        form.set('name', '咸鱼');

        fetch(url, {
            method: 'post',
            body: form
        })
            .then(res => {
                if (!res.ok) {
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
            method: 'post',
            body: form
        })
            .then(res => {
                if (!res.ok) {
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

    window.upload_file_base64 = function (file) {
        let url = "http://localhost:8080/upload_base64";

        img2base64(file).then(data => {
            let form = new FormData();
            form.append('base64', data);
            return form;
        })
            .then(form => {
                return fetch(url, {
                    method: 'post',
                    body: form
                })
            })
            .then(res => {

            })
            .then(json => {
                log(json);
            })
            .catch(error => {
                log(error.message);
            });
    };

    window.download_file = function () {
        const url = "http://localhost:8080/download/" + encodeURI("设计模式之禅高清扫描版.pdf");

        fetch(url, {
            method: 'get'
        }).then(res => {
            if (!res.ok) {
                throw new Error("HTTP error, status = " + res.status);
            }
            return res.json();
        }).then(json => {
            log(json);
        }).catch(error => {
            log(error);
        });
    };

    window.refreshToken = function () {
        const url = "http://localhost:8080/refreshToken";
        const token = localStorage.getItem('token');
        fetch(url, {
            method : "get",
            headers : {
                'Authorization' : token
            }
        }).then(res => {
            if (!res.ok){
                throw new Error("HTTP error, status = " + res.status);
            }
            return res.json();
        }).then(json => {
            localStorage.setItem('token', json.token);
            log(json);
        }).catch(error => {
            log(error);
        });
    };


    window.accessToken = function (id) {
        const url = "http://localhost:8080/accessToken/" + id;

        fetch(url, {
            method : 'get'
        }).then(res => {
            if (!res.ok) {
                throw new Error("HTTP error, status = " + res.status);
            }
            return res.json();
        }).then(json => {
            let token = localStorage.getItem('token');
            if (token == null){
                localStorage.setItem('token', json.token);
            } else {
                if (token !== json.token){
                    localStorage.setItem('token', json.token);
                }
            }
            log(json);
        }).catch(error => {
            log(error);
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