import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { createAuthAxios } from '../api/axiosInstance'
import ErrorAlert from '../components/ErrorAlert'

export default function Login() {
    const navigate = useNavigate()
    const { login } = useAuth()
    const [form, setForm] = useState({ username: '', password: '' })
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value })

    const handleSubmit = async (e) => {
        e.preventDefault()
        setError('')
        setLoading(true)
        try {
            // Verify credentials against the protected endpoint.
            // A 200 (even with empty list) = valid credentials.
            // A 401 = wrong credentials.
            const testAxios = createAuthAxios(form.username, form.password)
            await testAxios.get('/prompt/view-prompts')
            login(form.username, form.password)
            navigate('/dashboard')
        } catch (err) {
            if (err.response?.status === 401) {
                setError('Invalid username or password.')
            } else {
                setError(err.response?.data?.message || err.message || 'Login failed.')
            }
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center p-4">
            <div className="w-full max-w-md">
                {/* Logo / heading */}
                <div className="text-center mb-8">
                    <div className="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-violet-600 mb-4 shadow-lg shadow-violet-900/50">
                        <svg className="w-7 h-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                            <path strokeLinecap="round" strokeLinejoin="round" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                        </svg>
                    </div>
                    <h1 className="text-3xl font-bold text-white tracking-tight">AI Prompt Vault</h1>
                    <p className="text-gray-400 mt-1">Sign in to your vault</p>
                </div>

                <div className="bg-gray-900 rounded-2xl border border-gray-800 p-8 shadow-2xl">
                    {error && <ErrorAlert message={error} onDismiss={() => setError('')} />}

                    <form onSubmit={handleSubmit} className="space-y-5">
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1.5">Username</label>
                            <input
                                name="username"
                                value={form.username}
                                onChange={handleChange}
                                required
                                placeholder="Your username"
                                autoComplete="username"
                                className="w-full px-4 py-2.5 rounded-lg bg-gray-800 border border-gray-700 text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-violet-500 focus:border-transparent transition"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-300 mb-1.5">Password</label>
                            <input
                                name="password"
                                type="password"
                                value={form.password}
                                onChange={handleChange}
                                required
                                placeholder="Your password"
                                autoComplete="current-password"
                                className="w-full px-4 py-2.5 rounded-lg bg-gray-800 border border-gray-700 text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-violet-500 focus:border-transparent transition"
                            />
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full py-2.5 px-4 rounded-lg font-semibold text-white bg-violet-600 hover:bg-violet-500 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 shadow-lg shadow-violet-900/40"
                        >
                            {loading ? 'Signing in…' : 'Sign In'}
                        </button>
                    </form>

                    <p className="text-center text-sm text-gray-500 mt-6">
                        Don't have an account?{' '}
                        <Link to="/signup" className="text-violet-400 hover:text-violet-300 font-medium transition">
                            Sign up
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    )
}
