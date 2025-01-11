FROM mysql:latest

# Set the root password (for security, use environment variables in production)
ENV MYSQL_ROOT_PASSWORD=root

# Expose the MySQL port
EXPOSE 8000