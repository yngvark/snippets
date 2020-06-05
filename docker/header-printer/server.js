const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
    res.send('Hello World!')
    console.log('Headers:')
    console.log(JSON.stringify(req.headers));
})


app.listen(port, () => console.log(`Listening at http://localhost:${port}`))
