import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Chart from "./modules/Chart";

function App() {
  const [hello, setHello] = useState('')

  useEffect(() => {
    axios.get('/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
  }, []);

  return (
      <div>
        백엔드에서 가져온 데이터입니다asd : {hello}
          <Chart/>
      </div>
  );
}

export default App;