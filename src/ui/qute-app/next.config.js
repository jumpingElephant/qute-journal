/** @type {import('next').NextConfig} */
const isDevelopment = process.env.NODE_ENV !== 'production'
/** https://nextjs.org/docs/api-reference/next.config.js/rewrites **/
const rewritesConfig = isDevelopment || true
    ? [
        {
            source: '/tasks',
            destination: 'http://0.0.0.0:8080/tasks'
        },
        {
            source: '/tasks/:path*',
            destination: 'http://0.0.0.0:8080/tasks/:path*'
        }, {
            source: '/dev/:path*',
            destination: 'http://0.0.0.0:8080/dev/:path*'
        }
    ]
    : [];

const nextConfig = {
    reactStrictMode: true,
    swcMinify: true,
    experimental: {
        appDir: true
    },
    rewrites: async () => rewritesConfig
}

module.exports = nextConfig;
