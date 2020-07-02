const express = require('express')
const app = express()
const port = 3000

function logz(msg) {
    log = {
        message: msg
    }
    console.log(JSON.stringify(log))
}

app.get('/', (req, res) => {
    res.send('Hello World!')
    logz('Headers:')

    headerLog = {
        ...req.headers,
        ...{ message: 'print headers' }
    }
    //console.log(req.headers, null, 2);
    console.log(JSON.stringify(headerLog));
})


app.listen(port, () => console.log(`Listening at http://localhost:${port}`))
