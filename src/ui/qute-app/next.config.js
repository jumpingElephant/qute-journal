/** @type {import('next').NextConfig} */
const isDevelopment = process.env.NODE_ENV !== "production"
const rewritesConfig = isDevelopment
    ? [
        {
            source: "/tasks",
            destination: "http://localhost:8080/tasks"
        }
    ]
    : [];

const nextConfig = {
    reactStrictMode: true,
    swcMinify: true,
    rewrites: async () => rewritesConfig
}

module.exports = nextConfig;
