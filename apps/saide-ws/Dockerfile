FROM node:20-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install

EXPOSE 80

CMD HOST=0.0.0.0 PORT=80 npx y-websocket
