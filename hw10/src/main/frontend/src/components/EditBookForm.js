import React, { useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    FormControl,
    InputLabel,
    Select,
    MenuItem
} from '@mui/material';

const EditBookForm = ({ book, authors, genres, open, onClose, onSave }) => {
    const [formData, setFormData] = useState({
        id: book?.id || '',
        title: book?.title || '',
        authorId: book?.author?.id || '',
        genreId: book?.genre?.id || ''
    });

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
            const response = await fetch(`/api/books/${book.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error('Ошибка при обновлении книги');
            }

            const updatedBook = await response.json();
            onSave(updatedBook);
            onClose();
        } catch (err) {
            console.error('Ошибка при обновлении книги:', err);
        }
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Редактировать книгу</DialogTitle>
            <DialogContent>
                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '20px', marginTop: '10px' }}>
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
                </form>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Отмена</Button>
                <Button onClick={handleSubmit} variant="contained" color="primary">
                    Сохранить
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EditBookForm; 