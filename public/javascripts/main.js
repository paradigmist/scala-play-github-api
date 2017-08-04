var url = "/github/"

function call() {
  var input = document.getElementById('input').value;
  // console.log("url is:", url+input)

  fetch(url+input)
    .then(res => res.json())
    .then(data => {
    	let contributors_urls = data.map(obj => obj.contributors_url)
    	, languages_urls = data.map(obj => obj.languages_url)
    	, details = languages_urls.concat(contributors_urls);

    	var promises = details.map(url => fetch(url).then(y => y.json()));
    	Promise.all(promises).then(results => {
    	    data.map((obj,i) => {
    	    	obj.languages_url = results[i]
    	    	obj.contributors_url = results[(data.length) + i]
    	    	return obj
    	    });
    	    // console.log('details data', JSON.stringify(data, null, 2));
    	    document.getElementById('content').innerHTML = JSON.stringify(data, null, 2)
    	});

    })

    .catch(err => console.log('err', err));
}