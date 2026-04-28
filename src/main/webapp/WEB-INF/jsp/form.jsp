<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>${book.id == null ? 'Add Book' : 'Edit Book'}</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
        }
        .form-container {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #34495e;
        }
        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        input:focus, select:focus {
            border-color: #3498db;
            outline: none;
        }
        .btn-submit {
            width: 100%;
            padding: 10px;
            background-color: #3498db;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
            transition: background 0.3s;
        }
        .btn-submit:hover {
            background-color: #2980b9;
        }
        .error {
            color: #e74c3c;
            margin-bottom: 15px;
            font-size: 14px;
            text-align: center;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #7f8c8d;
            text-decoration: none;
        }
        .back-link:hover {
            color: #34495e;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>${book.id == null ? 'Add New Book' : 'Edit Book'}</h2>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form:form action="${book.id == null ? '/books' : '/books/update/' += book.id}" method="post" modelAttribute="book">
            <div class="form-group">
                <label for="title">Book Title:</label>
                <form:input path="title" id="title" required="true" />
            </div>
            
            <div class="form-group">
                <label for="price">Price:</label>
                <form:input path="price" id="price" type="number" step="0.01" required="true" />
            </div>
            
            <div class="form-group">
                <label for="author">Author:</label>
                <form:select path="author.id" id="author" required="true">
                    <form:option value="" label="-- Select Author --" />
                    <form:options items="${authors}" itemValue="id" itemLabel="name" />
                </form:select>
            </div>
            
            <button type="submit" class="btn-submit">${book.id == null ? 'Save Book' : 'Update Book'}</button>
        </form:form>
        <a href="/books" class="back-link">Back to List</a>
    </div>
</body>
</html>
