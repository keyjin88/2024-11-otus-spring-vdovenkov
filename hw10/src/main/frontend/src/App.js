import React from 'react';
import './App.css';
import BookList from './components/BookList';
import './components/BookList.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Библиотека</h1>
      </header>
      <main>
        <BookList />
      </main>
    </div>
  );
}

export default App; 