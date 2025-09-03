FROM node:20 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build --prod

FROM nginx:alpine
COPY --from=build /app/dist/SadadUi/browser /usr/share/nginx/html
EXPOSE 80
