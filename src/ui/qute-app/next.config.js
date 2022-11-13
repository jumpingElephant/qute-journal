/** @type {import('next').NextConfig} */
const isDevelopment = process.env.NODE_ENV !== 'production'
/** https://nextjs.org/docs/api-reference/next.config.js/rewrites **/
const rewritesConfig = isDevelopment
    ? [
        {
            source: '/tasks',
            destination: 'http://localhost:8080/tasks'
        },
        {
            source: '/tasks/:path*',
            destination: 'http://localhost:8080/tasks/:path*'
        }
    ]
    : [];

const nextConfig = {
    reactStrictMode: true,
    swcMinify: true,
    rewrites: async () => rewritesConfig
}

module.exports = nextConfig;
