var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
    setTimeout(function() {
	res.send('respond with a resource');
    }, 1000);
});

module.exports = router;
