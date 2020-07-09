import React from 'react';
import logo from './logo.svg';
import './App.css';
import $ from 'jquery';

const activateLasers = function () {
    $.ajax({
        url: 'http://localhost:8080/Medical/user/login',
        dataType: 'json',
        data:{username:'admin',password:'123'},

        success: function(data) {
            alert(data.msg);   // 注意这里
        },
        error: function(xhr, status, err) {
            //console.error(this.props.url, status, err.toString());
        }
    });

}
let  udata;
const getUsers = function () {
    $.ajax({
        url: 'http://localhost:8080/Medical/user/getusers',
        dataType: 'json',

        success: function(data) {
            if(data.success)
            {
                udata = data.data;
                $('#userlist').text(JSON.stringify(udata));
            }
            else
            {
            alert(data.msg);   }
        },
        error: function(xhr, status, err) {
            //console.error(this.props.url, status, err.toString());
        }
    });
}
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
          <button onClick={activateLasers}>
              激活按钮
          </button>
          <button onClick={getUsers}>
              获取用户列表
          </button>
          <div id='userlist'>{udata}</div>
      </header>
    </div>
  );
}

export default App;
