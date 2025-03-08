import React, { useState, useEffect } from 'react';
import './AddBookForm.css';

const AddBookForm = ({ onBookAdded }) => {
    const [authors, setAuthors] = useState([]);
    const [genres, setGenres] = useState([]);
    const [formData, setFormData] = useState({
        title: '',
        authorId: '',
        genreId: ''
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([
            fetch('/api/authors').then(res => res.json()),
            fetch('/api/genres').then(res => res.json())
        ])
            .then(([authorsData, genresData]) => {
                setAuthors(authorsData);
                setGenres(genresData);
                setLoading(false);
            })
            .catch(err => {
                setError('Ошибка при загрузке данных');
                setLoading(false);
            });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('/api/books', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error('Ошибка при добавлении книги');
            }

            const newBook = await response.json();
            onBookAdded(newBook);
            setFormData({
                title: '',
                authorId: '',
                genreId: ''
            });
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <div>Загрузка...</div>;
    if (error) return <div>Ошибка: {error}</div>;

    return (
        <div className="add-book-form">
            <h3>Добавить новую книгу</h3>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="title">Название:</label>
                    <input
                        type="text"
                        id="title"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="authorId">Автор:</label>
                    <select
                        id="authorId"
                        name="authorId"
                        value={formData.authorId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Выберите автора</option>
                        {authors.map(author => (
                            <option key={author.id} value={author.id}>
                                {author.fullName}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="genreId">Жанр:</label>
                    <select
                        id="genreId"
                        name="genreId"
                        value={formData.genreId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Выберите жанр</option>
                        {genres.map(genre => (
                            <option key={genre.id} value={genre.id}>
                                {genre.name}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit">Добавить книгу</button>
            </form>
        </div>
    );
};

export default AddBookForm; 