import React, { useState, useEffect } from 'react';
import {
    TextField,
    MenuItem,
    Button,
    Paper,
    Typography,
    Box,
    FormControl,
    InputLabel,
    Select
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

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

    if (loading) return <Typography>Загрузка...</Typography>;
    if (error) return <Typography color="error">Ошибка: {error}</Typography>;

    return (
        <Paper elevation={3} sx={{ p: 3 }}>
            <Typography variant="h5" component="h3" sx={{ mb: 3, textAlign: 'center', color: 'primary.main' }}>
                Добавить новую книгу
            </Typography>
            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                    label="Название"
                    name="title"
                    value={formData.title}
                    onChange={handleChange}
                    required
                    fullWidth
                />
                <FormControl fullWidth required>
                    <InputLabel>Автор</InputLabel>
                    <Select
                        name="authorId"
                        value={formData.authorId}
                        onChange={handleChange}
                        label="Автор"
                    >
                        {authors.map(author => (
                            <MenuItem key={author.id} value={author.id}>
                                {author.fullName}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <FormControl fullWidth required>
                    <InputLabel>Жанр</InputLabel>
                    <Select
                        name="genreId"
                        value={formData.genreId}
                        onChange={handleChange}
                        label="Жанр"
                    >
                        {genres.map(genre => (
                            <MenuItem key={genre.id} value={genre.id}>
                                {genre.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button
                    type="submit"
                    variant="contained"
                    startIcon={<AddIcon />}
                    sx={{ mt: 2 }}
                >
                    Добавить книгу
                </Button>
            </Box>
        </Paper>
    );
};

export default AddBookForm; 