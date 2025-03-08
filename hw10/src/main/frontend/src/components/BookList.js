import React, { useState, useEffect } from 'react';
import AddBookForm from './AddBookForm';
import { 
    Table, 
    TableBody, 
    TableCell, 
    TableContainer, 
    TableHead, 
    TableRow, 
    Paper,
    Typography,
    Container,
    Box
} from '@mui/material';

const BookList = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        try {
            const response = await fetch('/api/books');
            if (!response.ok) {
                throw new Error('Не удалось загрузить список книг');
            }
            const data = await response.json();
            setBooks(data);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    const handleBookAdded = (newBook) => {
        setBooks(prevBooks => [...prevBooks, newBook]);
    };

    if (loading) return <Typography>Загрузка...</Typography>;
    if (error) return <Typography color="error">Ошибка: {error}</Typography>;

    return (
        <Container maxWidth="md" sx={{ mt: 4 }}>
            <Typography variant="h4" component="h2" sx={{ my: 4, textAlign: 'center', color: 'primary.main' }}>
                Список книг
            </Typography>
            <TableContainer component={Paper} elevation={3}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Название</TableCell>
                            <TableCell>Автор</TableCell>
                            <TableCell>Жанр</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {books.map(book => (
                            <TableRow key={book.id} hover>
                                <TableCell>{book.title}</TableCell>
                                <TableCell>{book.author.fullName}</TableCell>
                                <TableCell>{book.genre.name}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Box sx={{ mt: 4 }}>
                <AddBookForm onBookAdded={handleBookAdded} />
            </Box>
        </Container>
    );
};

export default BookList; 