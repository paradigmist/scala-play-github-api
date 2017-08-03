var url = "http://localhost:9000?input="

function call() {
  var input = document.getElementById('input').value;
  fetch(url+input)
    .then(function(res) {return res.json();})
    .then(function(data) {
      document.getElementById('content').innerHTML = data;
    })
    .catch(function(err) {console.log('err', err);});
}