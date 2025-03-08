const handleResponse = async (response) => {
    if (!response.ok) {
        const error = await response.text();
        throw new Error(error || 'Произошла ошибка при выполнении запроса');
    }
    return response.json();
};

export const fetchBooks = async () => {
    const response = await fetch('/api/books');
    return handleResponse(response);
};

export const fetchAuthors = async () => {
    const response = await fetch('/api/authors');
    return handleResponse(response);
};

export const fetchGenres = async () => {
    const response = await fetch('/api/genres');
    return handleResponse(response);
};

export const createBook = async (bookData) => {
    const response = await fetch('/api/books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookData)
    });
    return handleResponse(response);
};

export const updateBook = async (id, bookData) => {
    const response = await fetch(`/api/books/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookData)
    });
    return handleResponse(response);
};

export const deleteBook = async (id) => {
    const response = await fetch(`/api/books/${id}`, {
        method: 'DELETE'
    });
    return handleResponse(response);
}; 